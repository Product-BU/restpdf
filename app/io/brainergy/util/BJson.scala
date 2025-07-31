/*
 * RestPDF
 * Copyright (C) 2025 Brainergy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package io.brainergy.util

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.core.json.JsonReadFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.databind.{DeserializationFeature, MapperFeature, PropertyNamingStrategies}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import io.brainergy.definitions.Ref

trait BJson {

  private val mapper: JsonMapper = JsonMapper.builder()
    .addModule(DefaultScalaModule)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
    .enable(JsonReadFeature.ALLOW_TRAILING_COMMA)
    .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
    .serializationInclusion(Include.NON_NULL)
    .build()

  def toOption[T](body: String, ref: Ref[T]): Option[T] = try {
    Option(mapper.readValue(body, ref))
  } catch {
    case t: Throwable => None
  }

  def toJsonString(a: Any):String = mapper.writeValueAsString(a)
}
