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
package io.brainergy.pdf.sign

case class SignAppearanceForm(pdfB64: String = ""
                              , pdfPath: String = ""
                              , docClosed: Boolean
                              , docPassword: Option[String]
                              , signatures: List[SignatureInfo]) {
  def hasPassword: Boolean = docPassword.isDefined && docPassword.get.nonEmpty
}

case class SignatureInfo(imgB64: String
                         , order: Int
                         , pageNo: Int
                         , x: Float
                         , y: Float
                         , width: Float
                         , height: Float
                         , keyAlias: String
                         , certAlias: String)
