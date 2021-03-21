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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by gridgain on 23.03.2016.
 */
public class AllTypesIndexed extends Timestamped {
    /**
     * Data Long.
     */
    @QuerySqlField(index = true)
    public Long longCol;

    /**
     * Data double.
     */
    @QuerySqlField()
    public double doubleCol;

    /**
     * Data String.
     */
    @QuerySqlField()
    public String stringCol;

    /**
     * Data boolean.
     */
    @QuerySqlField()
    public boolean booleanCol;

    /**
     * Data int.
     */
    @QuerySqlField()
    public int intCol;

    /**
     * BigDecimal
     */
    public BigDecimal bigDecimalCol;

    /**
     * Data bytes array.
     */
    public byte[] bytesCol;

    /**
     * Data bytes array.
     */
    public Short shortCol;

    public InnerTypes innerTypesCol;

    public class InnerTypes implements Serializable {
        public Long longCol;

        public String stringCol;

        public ArrayList<Long> arrayListCol = new ArrayList<>();

        InnerTypes(Long key) {

            longCol = key;
            stringCol = Long.toString(key);
            Long m = key % 8;
            for (Integer i = 0; i < m; i++) {
                arrayListCol.add(key + i);
            }
        }

        /** {@inheritDoc} */
        @Override
        public String toString() {
            return "[Long=" + longCol +
                    ", String='" + stringCol + "'" +
                    ", ArrayList=" + arrayListCol.toString() +
                    "]";
        }
    }

    public enumType enumCol;

    public ConcurrentMap<String, String> mapCol = new ConcurrentHashMap<String, String>();

    public HashSet<String> hashSetCol = new HashSet<>();

    public enum enumType {
        ENUMTRUE, ENUMFALSE
    }

    private void init(Long key, String str) {
        longCol = key;
        doubleCol = Math.round(1000 * Math.log10(longCol.doubleValue()));
        bigDecimalCol = BigDecimal.valueOf(doubleCol);
        doubleCol = doubleCol / 100;
        stringCol = str;
        if (key % 2 == 0) {
            booleanCol = true;
            enumCol = enumType.ENUMTRUE;
            innerTypesCol = new InnerTypes(key);
        } else {
            booleanCol = false;
            enumCol = enumType.ENUMFALSE;
            innerTypesCol = null;
        }
        intCol = key.intValue();
        bytesCol = stringCol.getBytes();
        shortCol = longCol.shortValue();
        Long m = key % 7;
        for (Integer i = 0; i < m; i++) {
            mapCol.put("map_key_" + (key + i), "map_value_" + (key + i));
        }
        m = (key % 11) / 2;
        for (Integer i = 0; i < m; i++) {
            hashSetCol.add("hashset_" + (key + i));
        }
    }

    public AllTypesIndexed(Long key) {
        init(key, Long.toString(key));
    }

    public AllTypesIndexed(Long key, String str) {
        init(key, str);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        List hashSetCol_SortedList = new ArrayList(hashSetCol);
        Collections.sort(hashSetCol_SortedList);
        String mapCol_string = "{";
        for (String key : new TreeSet<String>(mapCol.keySet())) {
            mapCol_string += key + "=" + mapCol.get(key) + ", ";
        }
        if (mapCol_string.length() > 1) {
            mapCol_string = mapCol_string.substring(0, mapCol_string.length() - 2);
        }
        mapCol_string += "}";
        String innerTypesStr = "null";
        if (innerTypesCol != null)
            innerTypesStr = innerTypesCol.toString();
        return "AllTypes=[Long=" + longCol +
                ", double=" + doubleCol +
                ", String='" + stringCol +
                "', boolean=" + booleanCol +
                ", int=" + intCol +
                ", bigDecimal=" + bigDecimalCol.toString() +
                ", bytes=" + Arrays.toString(bytesCol) +
                ", short=" + shortCol +
                ", InnerTypes=" + innerTypesStr +
                ", Map=" + mapCol_string +
                ", HashSet=" + hashSetCol_SortedList.toString() +
                ", enum=" + enumCol.toString() +
                "]";
    }

}
