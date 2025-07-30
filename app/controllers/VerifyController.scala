/*
 * This file is part of Brainergy RestPDF.
 *
 * Brainergy RestPDF is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Brainergy RestPDF is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package controllers

import com.typesafe.scalalogging.LazyLogging
import io.brainergy.pdf.verify.VerifyFacade
import io.brainergy.util.BJson
import play.api.mvc.{Action, AnyContent}

import java.io.ByteArrayInputStream
import java.util.Base64
import javax.inject.{Inject, Singleton}

/**
 * @author Peerapat A on Feb 11, 2022
 */
@Singleton
class VerifyController @Inject()(facade: VerifyFacade)
  extends BasedController
    with LazyLogging
    with BJson {

  private lazy val D = Base64.getDecoder

  def verify: Action[AnyContent] = Action { implicit request =>
    request.body.asText
      .map(b64 => facade.processStrem(new ByteArrayInputStream(D.decode(b64.trim))))
      .map(r => Ok(toJsonString(r)))
      .getOrElse(BadRequest("Missing Data"))
  }

}
