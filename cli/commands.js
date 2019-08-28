/**
 * Copyright 2013-2019 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster project, see https://www.jhipster.tech/
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
const defaultCommands = {
//     'import-jdl': {
//         alias: 'k8s-helm',
//         default: false,
//         argument: ['jdlFiles...'],
//         cliOnly: true,
//         desc: `Create entities from the JDL file passed in argument.
//   By default everything is run in parallel. If you like to interact with the console use '--interactive' flag.`,
//         help: `
//     --skip-install        # Do not automatically install dependencies                              Default: false
//     --interactive         # Run generation in series so that questions can be interacted with      Default: false
//     --db                  # Provide DB option for the application when using skip-server flag
//     --json-only           # Generate only the JSON files and skip entity regeneration              Default: false
//     --ignore-application  # Ignores application generation                                         Default: false
//     --ignore-deployments  # Ignores deployments generation                                         Default: false
//     --skip-ui-grouping    # Disable the UI grouping behavior for entity client side code           Default: false
//
// Arguments:
//     jdlFiles  # The JDL file names  Type: String[]  Required: true
//
// Example:
//     jhipster import-jdl myfile.jdl
//     jhipster import-jdl myfile.jdl --interactive
//     jhipster import-jdl myfile1.jdl myfile2.jdl
//         `
//     },
    client: {
        default: true,
        desc: 'Create a new react application based on the selected options'
    },
    server: {
        desc: 'Create a new kotlin-with-spring-boot application based on the selected options'
    }
};

module.exports = {
    ...defaultCommands
};
