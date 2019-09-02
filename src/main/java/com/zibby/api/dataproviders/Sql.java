package com.zibby.api.dataproviders;

import com.zibby.api.utils.XProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sql implements IDataProvider {

    private final String query;
    private final String driver;
    private final String username;
    private final String password;
    private final String url;

    public Sql(String query, String driver, String username, String password, String url) {
        this.query = query;
        this.driver = driver;
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public Sql(String query) {
        this.query = query;
        this.driver = XProperties.getProperty("settings.db_driver");
        this.username = XProperties.getProperty("settings.db_username");
        this.password = XProperties.getProperty("settings.db_password");
        this.url = XProperties.getProperty("jdbc:mysql://localhost:3306/XPDatabase");
    }

    @Override
    public String[][] getData(String... columns) {
        return getDbRecords(columns);
    }

    private String[][] getDbRecords(String[] columnNames) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        List<String[]> neededData = new ArrayList<String[]>();
        try {
            con = getDBConnection();
            st = con.createStatement();
            rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            List<String> columnHeaders = getColumnHeaders(rsmd);
            List<Integer> neededIndexes = new ArrayList<Integer>();

            if (columnNames == null || columnNames.length == 0) {
                for (int i = 0; i < columnNames.length; ++i) {
                    neededIndexes.add(Integer.valueOf(i));
                }
            } else {
                for (String neededName : columnNames) {
                    int neededIndex = columnHeaders.indexOf(neededName) + 1;
                    if (neededIndex < 0) {
                        throw new RuntimeException("Column Name: \"" + neededName + "\" was not found in the csv");
                    }
                    neededIndexes.add(Integer.valueOf(neededIndex));
                }
            }
            int numNeededCols = neededIndexes.size();
            while (rs.next()) {
                String[] neededRows = new String[numNeededCols];
                for (int i = 1; i < numNeededCols + 1; i++) {
                    neededRows[i - 1] = rs.getString(neededIndexes.get(i - 1));
                }
                neededData.add(neededRows);
            }
            return neededData.toArray(new String[neededData.size()][]);

        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }

    private Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(this.driver);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return dbConnection;
    }

    private List<String> getColumnHeaders(ResultSetMetaData rsmd) {
        List<String> columnHeaders = new ArrayList<String>();
        try {
            for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                columnHeaders.add(rsmd.getColumnLabel(i));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columnHeaders;
    }

    public Map<String, Object> getExpectedResultsFromSql() {
        Map<String, Object> expectedResults = new HashMap<String, Object>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = getDBConnection();
            st = con.createStatement();
            rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            List<String> columnHeaders = getColumnHeaders(rsmd);
            while (rs.next()) {
                for (String columnHeader : columnHeaders) {
                    expectedResults.put(columnHeader, rs.getString(columnHeader));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        return expectedResults;
    }
}