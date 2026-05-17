<template>
  <div class="app-container training-dashboard">
    <el-skeleton v-if="loading" animated :rows="14" class="dashboard-skeleton" />

    <template v-else>
      <el-row :gutter="16" class="dashboard-row">
        <el-col :xs="24" :lg="8">
          <el-card class="dashboard-card summary-card" shadow="never">
            <div class="profile">
              <el-avatar :size="58" shape="square" :src="userSummary.avatar">
                {{ avatarText }}
              </el-avatar>
              <div class="profile-main">
                <div class="profile-name">
                  <span>{{ userSummary.displayName || '学员' }}</span>
                  <el-tag v-if="userSummary.statusText" size="small" type="success" effect="plain">
                    {{ userSummary.statusText }}
                  </el-tag>
                </div>
                <div class="profile-meta">
                  <span>工号：{{ userSummary.employeeNo || '-' }}</span>
                  <span>密级：{{ userSummary.secretLevel || '-' }}</span>
                </div>
              </div>
            </div>

            <div class="metric-grid">
              <div
                v-for="metric in userSummary.metrics"
                :key="metric.key"
                :class="['metric-card', metricClass(metric.key)]"
              >
                <div class="metric-label">{{ metric.label }}</div>
                <div class="metric-value">{{ metric.displayValue }}</div>
              </div>
            </div>
          </el-card>
        </el-col>

        <el-col :xs="24" :lg="10">
          <el-card class="dashboard-card trend-card" shadow="never">
            <template #header>
              <div class="card-header">
                <span>{{ currentYear }}年度学习统计趋势</span>
                <el-icon><DataLine /></el-icon>
              </div>
            </template>
            <div ref="trendChartRef" class="trend-chart"></div>
          </el-card>
        </el-col>

        <el-col :xs="24" :lg="6">
          <el-card class="dashboard-card quick-card" shadow="never">
            <template #header>
              <div class="card-header">
                <span>快捷入口</span>
                <el-icon><Collection /></el-icon>
              </div>
            </template>
            <el-empty description="暂未开放" :image-size="76" />
          </el-card>
        </el-col>
      </el-row>

      <el-row :gutter="16" class="dashboard-row">
        <el-col v-for="section in listSections" :key="section.key" :xs="24" :lg="12">
          <el-card class="dashboard-card list-card" shadow="never">
            <template #header>
              <div class="card-header">
                <span>{{ section.title }}</span>
                <el-button link type="primary" @click="handleViewAll(section.title)">查看全部</el-button>
              </div>
            </template>

            <el-empty v-if="!section.items.length" :description="section.emptyText" :image-size="72" />

            <div v-else class="dashboard-list">
              <div v-for="item in section.items" :key="item.itemId" class="dashboard-list-item">
                <div class="list-icon">
                  <el-icon><BellFilled /></el-icon>
                </div>

                <div class="list-content">
                  <div class="list-title" :title="item.title">{{ item.title }}</div>
                  <div v-if="item.subtitle" class="list-subtitle" :title="item.subtitle">
                    {{ item.subtitle }}
                  </div>
                  <div class="list-meta">
                    <span v-if="item.sourceLabel">{{ item.sourceLabel }}</span>
                    <span v-if="item.publishDateText">{{ item.publishDateText }}</span>
                    <span v-if="item.startTimeText || item.endTimeText">
                      {{ item.startTimeText || '-' }} 至 {{ item.endTimeText || '-' }}
                    </span>
                    <span v-if="item.ownerName">{{ item.ownerName }}</span>
                  </div>
                </div>

                <div class="list-actions">
                  <div class="tag-line">
                    <el-tag v-if="item.publicFlag === '1'" size="small" effect="plain">公开</el-tag>
                    <el-tag
                      v-if="item.statusText"
                      size="small"
                      :type="statusTagType(item.businessStatus)"
                      effect="plain"
                    >
                      {{ item.statusText }}
                    </el-tag>
                  </div>
                  <el-button link type="primary" class="action-btn" @click="handleItemAction(item)">
                    {{ item.actionText || '查看' }}
                    <el-icon><ArrowRight /></el-icon>
                  </el-button>
                </div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </template>
  </div>
</template>

<script setup>
import * as echarts from 'echarts'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getTrainingDashboard } from '@/api/training/dashboard'

const router = useRouter()
const currentYear = new Date().getFullYear()
const loading = ref(true)
const trendChartRef = ref(null)
let trendChart = null

const dashboard = ref(createEmptyDashboard())

const userSummary = computed(() => dashboard.value.userSummary)
const avatarText = computed(() => (userSummary.value.displayName || '学员').slice(0, 1))
const listSections = computed(() => [
  {
    key: 'todoProcesses',
    title: '我的待办流程',
    items: dashboard.value.todoProcesses,
    emptyText: '暂无待办流程'
  },
  {
    key: 'offlineNotices',
    title: '线下培训通知',
    items: dashboard.value.offlineNotices,
    emptyText: '暂无线下培训通知'
  },
  {
    key: 'onlineTrainings',
    title: '线上培训通知',
    items: dashboard.value.onlineTrainings,
    emptyText: '暂无线上培训'
  },
  {
    key: 'onlineExams',
    title: '在线考试通知',
    items: dashboard.value.onlineExams,
    emptyText: '暂无在线考试'
  }
])

onMounted(() => {
  loadDashboard()
  window.addEventListener('resize', resizeChart)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', resizeChart)
  if (trendChart) {
    trendChart.dispose()
    trendChart = null
  }
})

async function loadDashboard() {
  loading.value = true
  try {
    const res = await getTrainingDashboard({ year: currentYear })
    dashboard.value = normalizeDashboard(res.data)
    loading.value = false
    await nextTick()
    renderTrendChart()
  } catch (error) {
    dashboard.value = createEmptyDashboard()
    ElMessage.error('首页数据加载失败')
  } finally {
    loading.value = false
  }
}

function createEmptyDashboard() {
  return {
    userSummary: {
      displayName: '',
      avatar: '',
      employeeNo: '',
      secretLevel: '',
      statusText: '',
      metrics: []
    },
    trend: [],
    todoProcesses: [],
    offlineNotices: [],
    onlineTrainings: [],
    onlineExams: []
  }
}

function normalizeDashboard(data = {}) {
  const empty = createEmptyDashboard()
  return {
    ...empty,
    ...data,
    userSummary: {
      ...empty.userSummary,
      ...(data.userSummary || {}),
      metrics: data.userSummary?.metrics || []
    },
    trend: data.trend || [],
    todoProcesses: data.todoProcesses || [],
    offlineNotices: data.offlineNotices || [],
    onlineTrainings: data.onlineTrainings || [],
    onlineExams: data.onlineExams || []
  }
}

function renderTrendChart() {
  if (!trendChartRef.value) {
    return
  }
  if (!trendChart) {
    trendChart = echarts.init(trendChartRef.value)
  }

  const trend = dashboard.value.trend || []
  trendChart.setOption({
    color: ['#21b883', '#3b82f6'],
    tooltip: {
      trigger: 'axis',
      valueFormatter: value => formatHours(value)
    },
    legend: {
      top: 0,
      right: 0,
      data: ['学习时长', '培训时长']
    },
    grid: {
      left: 36,
      right: 18,
      top: 46,
      bottom: 28
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: trend.map(item => item.monthLabel),
      axisLine: { lineStyle: { color: '#e5e9f2' } },
      axisTick: { show: false },
      axisLabel: { color: '#8b97a8' }
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        color: '#8b97a8',
        formatter: value => formatHours(value)
      },
      splitLine: { lineStyle: { color: '#eef2f7' } }
    },
    series: [
      {
        name: '学习时长',
        type: 'line',
        smooth: true,
        symbolSize: 7,
        data: trend.map(item => Number(item.learningHours || 0))
      },
      {
        name: '培训时长',
        type: 'line',
        smooth: true,
        symbolSize: 7,
        data: trend.map(item => Number(item.trainingHours || 0))
      }
    ]
  })
}

function resizeChart() {
  if (trendChart) {
    trendChart.resize()
  }
}

function formatHours(value) {
  const num = Number(value || 0)
  return `${num.toFixed(1).replace(/\.0$/, '')}h`
}

function metricClass(key) {
  return {
    yearTrainingHours: 'metric-blue',
    yearLearningHours: 'metric-green',
    totalLearningHours: 'metric-neutral',
    totalTrainingHours: 'metric-purple'
  }[key] || 'metric-neutral'
}

function statusTagType(status) {
  return {
    LEARNING: 'success',
    COMPLETED: 'success',
    IN_PROGRESS: 'warning',
    PENDING: 'info',
    NOT_STARTED: 'info',
    OPEN: 'primary'
  }[status] || 'info'
}

function handleViewAll(title) {
  ElMessage.info(`${title}列表待接入`)
}

function handleItemAction(item) {
  if (item.actionType === 'EXAM_CONFIRM') {
    ElMessageBox.confirm(`确认开始「${item.title}」吗？`, '考试确认', {
      confirmButtonText: '开始考试',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      if (item.actionPath) {
        router.push(item.actionPath)
      } else {
        ElMessage.success('已确认，考试页面待接入')
      }
    }).catch(() => {})
    return
  }

  if (item.actionPath) {
    router.push(item.actionPath)
  } else {
    ElMessage.info(`${item.actionText || '操作'}功能待接入`)
  }
}
</script>

<style lang="scss" scoped>
.training-dashboard {
  min-height: calc(100vh - 84px);
  background: #f5f7fb;
}

.dashboard-skeleton {
  padding: 12px;
  background: #fff;
  border-radius: 8px;
}

.dashboard-row {
  row-gap: 16px;
  margin-bottom: 16px;
}

.dashboard-card {
  height: 100%;
  border: 1px solid #edf0f5;
  border-radius: 8px;

  :deep(.el-card__header) {
    padding: 16px 20px;
    border-bottom: 1px solid #f0f2f5;
  }

  :deep(.el-card__body) {
    padding: 20px;
  }
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  min-height: 24px;
  color: #1f2d3d;
  font-size: 16px;
  font-weight: 600;
}

.summary-card {
  .profile {
    display: flex;
    align-items: center;
    gap: 14px;
    margin-bottom: 22px;
  }

  .profile-main {
    min-width: 0;
  }

  .profile-name {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-bottom: 8px;
    color: #1f2d3d;
    font-size: 18px;
    font-weight: 700;
  }

  .profile-meta {
    display: flex;
    flex-wrap: wrap;
    gap: 8px 14px;
    color: #7a8798;
    font-size: 13px;
  }
}

.metric-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;
}

.metric-card {
  min-height: 76px;
  padding: 14px;
  border: 1px solid #eef2f7;
  border-radius: 8px;
  background: #fbfcff;
}

.metric-label {
  color: #7a8798;
  font-size: 13px;
}

.metric-value {
  margin-top: 10px;
  color: #1f2d3d;
  font-size: 22px;
  font-weight: 700;
}

.metric-blue .metric-value {
  color: #3b82f6;
}

.metric-green .metric-value {
  color: #21b883;
}

.metric-purple .metric-value {
  color: #6d5dfc;
}

.trend-chart {
  width: 100%;
  height: 230px;
}

.quick-card {
  :deep(.el-empty) {
    padding: 18px 0 20px;
  }
}

.list-card {
  min-height: 294px;
}

.dashboard-list {
  display: flex;
  flex-direction: column;
}

.dashboard-list-item {
  display: flex;
  gap: 12px;
  min-height: 68px;
  padding: 12px 0;
  border-bottom: 1px solid #f0f2f5;
}

.dashboard-list-item:last-child {
  border-bottom: none;
}

.list-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  flex: 0 0 30px;
  width: 30px;
  height: 30px;
  margin-top: 2px;
  border-radius: 8px;
  background: #f0f5ff;
  color: #3b82f6;
}

.list-content {
  min-width: 0;
  flex: 1;
}

.list-title {
  overflow: hidden;
  color: #1f2d3d;
  font-size: 14px;
  font-weight: 600;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.list-subtitle {
  overflow: hidden;
  margin-top: 4px;
  color: #7a8798;
  font-size: 12px;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.list-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 8px 14px;
  margin-top: 6px;
  color: #a8b2c1;
  font-size: 12px;
}

.list-actions {
  display: flex;
  flex: 0 0 150px;
  flex-direction: column;
  align-items: flex-end;
  justify-content: space-between;
  gap: 8px;
}

.tag-line {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-end;
  gap: 6px;
}

.action-btn {
  font-weight: 600;
}

@media screen and (max-width: 1199px) {
  .training-dashboard {
    min-height: calc(100vh - 84px);
  }

  .list-actions {
    flex-basis: 130px;
  }
}

@media screen and (max-width: 767px) {
  .training-dashboard {
    padding: 12px;
  }

  .metric-grid {
    grid-template-columns: 1fr;
  }

  .dashboard-list-item {
    flex-wrap: wrap;
  }

  .list-actions {
    flex: 1 1 100%;
    flex-direction: row;
    align-items: center;
    justify-content: flex-end;
    padding-left: 42px;
  }

  .list-title {
    white-space: normal;
  }
}
</style>
