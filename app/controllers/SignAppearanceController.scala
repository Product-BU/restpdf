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
import io.brainergy.definitions.Ref
import io.brainergy.pdf.sign.{SignAppearanceFacade, SignAppearanceForm}
import io.brainergy.util.BJson

import javax.inject.{Inject, Singleton}

/**
 * @author Peerapat A on Feb 11, 2022
 */
@Singleton
class SignAppearanceController @Inject()(facade: SignAppearanceFacade)
  extends BasedController
    with LazyLogging
    with BJson {

  def sign = Action(parse.byteString) { implicit request =>
    if (hasAuthorized())
      toOption(request.body.decodeString("UTF-8"), new Ref[SignAppearanceForm] {})
        .map(f => Ok(facade(f).toByteArray).as("file/bin"))
        .getOrElse(BadRequest("invalid json body"))
    else
      Unauthorized("Token Invalid")
  }
}