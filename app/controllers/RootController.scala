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

import java.security.Security

import io.brainergy.util.{Conf, SSLCertificate}
import javax.inject.Singleton
import org.bouncycastle.jce.provider.BouncyCastleProvider
import play.api.mvc.{Action, AnyContent}

/**
 * @author Peerapat A on Feb 11, 2022
 */
@Singleton
class RootController extends BasedController
  with SSLCertificate {

  trustAllCert()
  Security.addProvider(new BouncyCastleProvider)

  private val version: String = Conf("build.version", "NA")
  private val commitId: String = Conf("build.commit.id", "NA")

  def rootOptions: Action[AnyContent] = options("/")

  def options(url: String): Action[AnyContent] = Action {
    NoContent
  }

  def buildInfo: Action[AnyContent] = Action {
    Ok(s"""{ "version":"$version", "commit_id":"$commitId" }""")
      .as(JSON)
  }

}
