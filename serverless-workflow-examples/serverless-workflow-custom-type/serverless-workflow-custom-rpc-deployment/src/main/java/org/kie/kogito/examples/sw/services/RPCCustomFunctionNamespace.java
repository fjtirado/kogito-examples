/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.kogito.examples.sw.services;

import org.jbpm.ruleflow.core.RuleFlowNodeContainerFactory;
import org.jbpm.ruleflow.core.factory.WorkItemNodeFactory;
import org.kie.kogito.serverless.workflow.functions.WorkItemFunctionNamespace;
import org.kie.kogito.serverless.workflow.parser.ParserContext;

import io.serverlessworkflow.api.Workflow;
import io.serverlessworkflow.api.functions.FunctionRef;

import static org.kie.kogito.examples.sw.custom.RPCCustomWorkItemHandler.NAME;
import static org.kie.kogito.examples.sw.custom.RPCCustomWorkItemHandler.OPERATION;
import static org.kie.kogito.serverless.workflow.parser.FunctionNamespaceFactory.getFunctionName;

public class RPCCustomFunctionNamespace extends WorkItemFunctionNamespace{

    @Override
    public String namespace() {
        return "rpc";
    }

    @Override
    protected <T extends RuleFlowNodeContainerFactory<T, ?>> WorkItemNodeFactory<T> fillWorkItemHandler(Workflow workflow,
                                                                                                        ParserContext context,
                                                                                                        WorkItemNodeFactory<T> node,
                                                                                                        FunctionRef functionRef) {
        return node.workName(NAME).metaData(OPERATION,  getFunctionName(functionRef));
    }
}
