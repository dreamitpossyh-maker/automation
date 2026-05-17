package com.ruoyi.system.service;

import java.math.BigDecimal;

class TrainingDashboardFormatterTest
{
    public static void main(String[] args)
    {
        assertEquals("128h", TrainingDashboardFormatter.formatHours(new BigDecimal("128.00")));
        assertEquals("12.3h", TrainingDashboardFormatter.formatHours(new BigDecimal("12.34")));
        assertEquals("0h", TrainingDashboardFormatter.formatHours(null));
        System.out.println("TrainingDashboardFormatterTest passed");
    }

    private static void assertEquals(String expected, String actual)
    {
        if (!expected.equals(actual))
        {
            throw new AssertionError("expected=" + expected + ", actual=" + actual);
        }
    }
}
