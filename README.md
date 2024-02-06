# GameTuner Scala Stream Collector

## Overview

GameTuner Scala Stream Collector is a scala application for collecting raw events over HTTP. Collector is build upon [Snowplow Scala Stream Collector][snowplow-collector]. It is a part of the GameTuner project, which is a project for collecting and analyzing game data. The collector is responsible for collecting data from the game and sending it to the GameTuner pipeline.

Since GameTuner project is build for GCP (Google Cloud Platform), the collector is build to be deployed on GCP. For that reason only pubsub module of application is supported.

## Requirements

For building and running the application you need:

- sbt >= 1.5.6
- scala = 2.12.10
- jdk = 11

## Installation

### Run locally

Once you open the project in your IDE, first you should build the project by running `sbt 'project pubsub' assembly` in the root of the project. 

Since the application is build for GCP, you should have a GCP account and a project created, because proceesed events should be streamed to pubsub topic. Once you create a project and a topic, you should setup config file for the application. Config file is located in [`config/collector.conf`][config-file]. You should set the following parameters:

- `collector.streams.good`                 - pubsub tobic for good events
- `collector.streams.bad`                  - pubsub topic for bad events
- `collector.streams.sink.googleProjectId` - GCP project id



After that you can run the application by running the main class [`GooglePubSubCollector.scala`][run-class] in `pubsub` module or by running the jar file with the following command:

```bash
java -jar <path-to-jar> --config /config/collector.config
```

Collector is now running and listening for events on the specified port, bu default it is 9292. You can send events to the collector by sending POST request to the collector url `http://0.0.0.0:9292/com.snowplowanalytics.snowplow/tp2/` with the payload in the body of the request. Example of the payload is shown below.

```json
{
    "schema": "iglu:com.algebraai.gametuner.common/payload_data/jsonschema/1-0-0",
    "data": [
       {
        "e":"ue",
        "ue_pr":"{\"schema\":\"iglu:com.snowplowanalytics.snowplow/unstruct_event/jsonschema/1-0-0\",\"data\":{\"schema\":\"iglu:com.algebraai.gametuner.event/new_user/jsonschema/1-0-0\",\"data\":null}}",
        "eid":"00000000-0000-0000-0000-000000000000",
        "dtm":"1673451491326",
        "stm":"1673451492032",
        "p":"android",
        "aid":"demoapp",
        "tna":"Gametuner.Unity",
        "tv":"postman-0.0.00",
        "uid":"user_id",
        "iid":"installation_id",
        "ak":"api_key",
        "sm":"1",
        "co":"{\"schema\":\"iglu:com.snowplowanalytics.snowplow/contexts/jsonschema/1-0-1\",\"data\":[{\"schema\":\"iglu:com.algebraai.gametuner.context/event_context/jsonschema/1-0-0\",\"data\":{\"event_index\":0,\"previous_event\":\"None\",\"sandbox_mode\":true,\"event_bundle_id\":0,\"is_online\":true}},{\"schema\":\"iglu:com.algebraai.gametuner.embedded_context/session_context/jsonschema/1-0-0\",\"data\":{\"session_id\":\"cea50377-fe8f-4563-a263-274a43e0c7bd\",\"session_index\":1,\"session_time\":0.0}},{\"schema\":\"iglu:com.algebraai.gametuner.embedded_context/device_context/jsonschema/1-0-0\",\"data\":{\"advertising_id\":\"b0993057-edb6-480c-baa7-5a9396bf324d\",\"build_version\":\"1.000.00\",\"cpu_type\":\"ARM64\",\"device_category\":\"mobile\",\"device_id\":\"00000000000000000\",\"device_language\":\"en\",\"device_manufacturer\":\"Apple\",\"model\":\"13\",\"device_timezone\":\"+00:00\",\"gpu\":\"Apple GPU\",\"is_hacked\":\"Sandboxed\",\"os_version\":\"IOS 14.5.1\",\"ram_size\":7461,\"screen_resolution\":\"1080x2400\"}}]}"
        }
    ]
}
```

### Deploy on GCP

For deploying the application on GCP, you should first build the application by running google cloud build `gcloud builds submit --config=cloudbuild.yaml .`. Script submits docker image to GCP artifact registry. Once the image is submitted, you should deploy the application on GCP. You can do that by running terraform script in [GameTuner terraform][gametune-terraform] project.

## Licence

This project is fork of [Snowplow Scala Stream Collector version 2.7.0][snowplow-collector-2.7.0], that is licenced under Apache 2.0 Licence.

The GameTuner Scala Stream Collector is copyright 2022-2024 AlgebraAI.

GameTuner Scala Stream Collector is released under the [Apache 2.0 License][license].

[snowplow-collector]:https://github.com/snowplow/stream-collector
[run-class]:pubsub/src/main/scala/com.snowplowanalytics.snowplow.collectors.scalastream/GooglePubSubCollector.scala
[config-file]:config/collector.config
[gametune-terraform]:https://github.com/GameTuner/gametuner-terraform-gcp.git
[snowplow-collector-2.7.0]:https://github.com/snowplow/stream-collector/releases/tag/2.7.0
[license]: https://www.apache.org/licenses/LICENSE-2.0