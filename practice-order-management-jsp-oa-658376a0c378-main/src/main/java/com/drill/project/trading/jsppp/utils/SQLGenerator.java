package com.drill.project.trading.jsppp.utils;

import java.util.StringJoiner;

//goal: generate SQL statements dynamically instead of hardcoding question marks
public class SQLGenerator {
    public static String buildInsertSql(String tableName, String... columns) {
        StringJoiner columnsJoiner = new StringJoiner(", ");
        StringJoiner placeholdersJoiner = new StringJoiner(", ");
        for (String column : columns) {
            columnsJoiner.add(column);
            placeholdersJoiner.add("?");
        }

        return String.format(
                "INSERT INTO %s (%s) VALUES (%s)",
                tableName, columnsJoiner, placeholdersJoiner);
    }

    public static String buildUpdateSql(String tableName, String idColumn, String... columns) {

        StringJoiner setClauseJoiner = new StringJoiner(", ");
        for (String column : columns) {
            setClauseJoiner.add(column + " = ?");
        }

        return String.format(
                "UPDATE %s SET %s WHERE %s = ?",
                tableName, setClauseJoiner, idColumn);
    }

    public static String buildDeleteSql(String tableName, String idColumn) {
        return String.format(
                "DELETE FROM %s WHERE %s = ?",
                tableName, idColumn);
    }
}
