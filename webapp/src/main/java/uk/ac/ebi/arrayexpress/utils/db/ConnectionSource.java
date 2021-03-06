package uk.ac.ebi.arrayexpress.utils.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.pool.HikariPool;

import java.sql.Connection;
import java.sql.SQLException;

/*
 * Copyright 2009-2014 European Molecular Biology Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

public class ConnectionSource implements IConnectionSource
{
    private HikariPool connectionPool;
    private String name;

    public ConnectionSource( String name, HikariConfig cpConfig ) throws SQLException
    {
        this.name = name;
        this.connectionPool = new HikariPool(cpConfig);
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public Connection getConnection() throws SQLException
    {
        if (null != this.connectionPool) {
            return this.connectionPool.getConnection();
        } else {
            throw new SQLException("Unable to obtain a connection from pool [" + this.name + "], pool is not created or closed");
        }
    }

    @Override
    public void close() throws InterruptedException
    {
        this.connectionPool.shutdown();
        this.connectionPool = null;
    }
}