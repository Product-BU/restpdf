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
import play.api.mvc.{Action, AnyContent}

/**
 * @author Peerapat A on Feb 11, 2022
 */
@Singleton
class VerifyFileController @Inject()(facade: VerifyFacade)
  extends BasedController
    with LazyLogging
    with BJson {

  def verifyHTML: Action[AnyContent] = Action {
    Ok(
      """
        |<form method="POST" action="/pdf/verify" enctype="multipart/form-data">
        |  <input type="file" id="pdffile" name="pdffile">
        |  <input type="submit">
        |</form>
        |""".stripMargin).as(HTML)
  }

  def verifyFile = Action(parse.multipartFormData) { implicit request =>
    request.body
      .file("pdf_file")
      .map(tmpFile => try {
        val file = tmpFile.ref.toFile
        val result = toJsonString(facade(file.getAbsolutePath))
        logger.info(s"Process file -> ${file.getName}")
        logger.info(s" >> $result")
        Ok(result)
      } finally {
        tmpFile.ref.delete()
      }).getOrElse(BadRequest("Missing File"))
  }

}
