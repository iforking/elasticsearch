/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.index.mapper;

import org.apache.lucene.search.Query;
import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.SpatialStrategy;
import org.elasticsearch.geometry.Geometry;
import org.elasticsearch.index.query.QueryShardContext;

/**
 * a base class for geometry types that support shape query builder
 */
public abstract class AbstractSearchableGeometryFieldType extends MappedFieldType {

    protected QueryProcessor geometryQueryBuilder;

    protected AbstractSearchableGeometryFieldType() {
    }

    protected AbstractSearchableGeometryFieldType(AbstractSearchableGeometryFieldType ref) {
        super(ref);
    }

    public void setGeometryQueryBuilder(QueryProcessor geometryQueryBuilder)  {
        this.geometryQueryBuilder = geometryQueryBuilder;
    }

    public QueryProcessor geometryQueryBuilder() {
        return geometryQueryBuilder;
    }

    /**
     * interface representing a query builder that generates a query from the given shape
     */
    public interface QueryProcessor {
        Query process(Geometry shape, String fieldName, ShapeRelation relation, QueryShardContext context);

        @Deprecated
        default Query process(Geometry shape, String fieldName, SpatialStrategy strategy, ShapeRelation relation,
                              QueryShardContext context) {
            return process(shape, fieldName, relation, context);
        }
    }
}

