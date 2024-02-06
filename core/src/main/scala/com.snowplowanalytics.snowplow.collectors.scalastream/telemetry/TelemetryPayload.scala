/*
 * Copyright (c) 2013-2022 Snowplow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0, and
 * you may not use this file except in compliance with the Apache License
 * Version 2.0.  You may obtain a copy of the Apache License Version 2.0 at
 * http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Apache License Version 2.0 is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the Apache License Version 2.0 for the specific language
 * governing permissions and limitations there under.
 */
package com.snowplowanalytics.snowplow.collectors.scalastream.telemetry

// iglu:com.snowplowanalytics.oss/oss_context/jsonschema/1-0-1
private case class TelemetryPayload(
  userProvidedId: Option[String]  = None,
  moduleName: Option[String]      = None,
  moduleVersion: Option[String]   = None,
  instanceId: Option[String]      = None,
  region: Option[String]          = None,
  cloud: Option[CloudVendor]      = None,
  autoGeneratedId: Option[String] = None,
  applicationName: String,
  applicationVersion: String,
  appGeneratedId: String
)