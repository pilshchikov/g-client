/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.thick.models.datatypes;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by avolkov on 06.09.17.
 */
public class Account extends Transferable {
    /**
     * Account ID.
     */
    @QuerySqlField(index = true)
    private long id;

    /**
     * Last balance modification.
     */
    @QuerySqlField
    private Date lastModification;

    /**
     * Time stamp.
     */
    @QuerySqlField
    private String updateTimeStamp;

    /**
     * Type.
     */
    @QuerySqlField
    private String type;

    /**
     * @param id Account ID.
     */
    public Account(Long id) {
        this.id = id;
        lastModification = new Date();
        updateTimeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        type = id % 2 == 0 ? "CREDIT" : "DEBIT";
    }

    /**
     * Change balance by specified amount.
     *
     * @param amount Amount to add to balance (may be negative).
     */
    @Override public void updateBalance(long amount) {
        super.updateBalance(amount);
        lastModification = new Date();
        updateTimeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "Account [id=" + id + ", balance=$" + balance + ']';
    }
}

