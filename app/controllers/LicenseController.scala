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
package controllers

import com.typesafe.scalalogging.LazyLogging
import io.brainergy.pdf.verify.VerifyFacade
import io.brainergy.util.BJson
import javax.inject.{Inject, Singleton}

/**
 * @author Saroj C on Jul 31, 2025
 */
@Singleton
class LicenseController @Inject()(facade: VerifyFacade)
  extends BasedController
    with LazyLogging
    with BJson {

  def buildInfo: Action[AnyContent] = Action {
    Ok(s"""
    { 
      "projectName": "RestPDF",
      "version": "1.0",
      "license": "GNU Affero General Public License v3.0",
      "sourceCode": "https://github.com/Product-BU/restpdf",
      "notice": "This software is licensed under the AGPL v3. Users interacting with this service over a network have the right to receive the source code."
    }
    """)
      .as(JSON)
  }

}
