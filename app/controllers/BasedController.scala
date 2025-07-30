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

import io.brainergy.util.Conf
import play.api.mvc.{BaseController, ControllerComponents, Request}

import javax.inject.Inject

/**
 * @author Peerapat A on Feb 11, 2022
 */
abstract class BasedController extends BaseController {

  @Inject
  private var cc: ControllerComponents = _

  override def controllerComponents: ControllerComponents = cc

  def hasAuthorized[T]()(implicit request: Request[T]): Boolean = !unAuthorized()

  def unAuthorized[T]()(implicit request: Request[T]): Boolean = !request.headers.get("X-Access-Token").contains(TOKEN)

  private lazy val TOKEN = Conf("hsm.access")

}
