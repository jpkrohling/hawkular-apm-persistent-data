/*
 * Copyright 2017 Juraci Paixão Kröhling
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.apm.persistent.data;

import java.util.logging.Logger;

import org.hawkular.apm.persistent.data.boundary.HealthCheckServer;
import org.hawkular.apm.persistent.data.boundary.PersistentDataServer;
import org.hawkular.apm.persistent.data.control.Configuration;
import org.hawkular.apm.persistent.data.control.ConfigurationResources;

/**
 * @author Juraci Paixão Kröhling
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Hawkular APM Persistent Data is starting");
        Configuration configuration = ConfigurationResources.build(args).getConfiguration();
        try {
            PersistentDataServer.start(configuration.getBind(), configuration.getPort());
            HealthCheckServer.start(configuration.getHealthcheckBind(), configuration.getHealthcheckPort());
            logger.info(() -> String.format("Hawkular APM Persistent Data started at %s:%s", configuration.getBind(), configuration.getPort()));
            logger.info(() -> String.format("Hawkular APM Persistent Data Health Check started at %s:%s", configuration.getHealthcheckBind(), configuration.getHealthcheckPort()));
        } catch (Throwable t) {
            logger.severe(() -> String.format("Could not start the Hawkular APM Persistent Data. Reason: %s", t.getMessage()));
            System.exit(127);
        }
    }
}
