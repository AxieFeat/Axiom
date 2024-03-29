/*
 * Copyright 2022 Axiom contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.arial.axiom.elements;

public abstract class ObjectUnit {

    private String[] comments;

    public ObjectAssortment assortment() {
        return (ObjectAssortment) this;
    }

    public ObjectPrimitive primitives() {
        return (ObjectPrimitive) this;
    }

    public ObjectSeries series() {
        return (ObjectSeries) this;
    }

    public static final class Null extends ObjectUnit {

    }

    public String[] getComments() {
        return comments;
    }

    public void setComments(String[] comments) {
        this.comments = comments;
    }
}
