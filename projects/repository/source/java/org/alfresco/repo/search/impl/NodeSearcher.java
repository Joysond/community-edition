/*
 * Copyright (C) 2005 Alfresco, Inc.
 *
 * Licensed under the Mozilla Public License version 1.1 
 * with a permitted attribution clause. You may obtain a
 * copy of the License at
 *
 *   http://www.alfresco.org/legal/license.txt
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 */
package org.alfresco.repo.search.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.alfresco.repo.search.DocumentNavigator;
import org.alfresco.repo.search.NodeServiceXPath;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.XPathException;
import org.alfresco.service.cmr.search.QueryParameterDefinition;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.NamespacePrefixResolver;
import org.jaxen.JaxenException;

/**
 * Helper class that walks a node hierarchy.
 * <p>
 * Some searcher methods on {@link org.alfresco.service.cmr.search.SearchService}
 * can use this directly as its only dependencies are
 * {@link org.alfresco.service.cmr.repository.NodeService},
 * {@link org.alfresco.service.cmr.dictionary.DictionaryService} and
 * a {@link org.alfresco.service.cmr.search.SearchService}
 * 
 * @author Derek Hulley
 */
public class NodeSearcher
{
    private NodeService nodeService;
    private DictionaryService dictionaryService;
    private SearchService searchService;
    
    public NodeSearcher(
            NodeService nodeService,
            DictionaryService dictionaryService,
            SearchService searchService)
    {
        this.nodeService = nodeService;
        this.dictionaryService = dictionaryService;
        this.searchService = searchService;
    }

    /**
     * @see NodeServiceXPath
     */
    public synchronized List<NodeRef> selectNodes(
            NodeRef contextNodeRef,
            String xpath,
            QueryParameterDefinition[] paramDefs,
            NamespacePrefixResolver namespacePrefixResolver,
            boolean followAllParentLinks)
    {
        try
        {
            DocumentNavigator documentNavigator = new DocumentNavigator(
                    dictionaryService,
                    nodeService,
                    searchService,
                    namespacePrefixResolver,
                    followAllParentLinks);
            NodeServiceXPath nsXPath = new NodeServiceXPath(xpath, documentNavigator, paramDefs);
            for (String prefix : namespacePrefixResolver.getPrefixes())
            {
                nsXPath.addNamespace(prefix, namespacePrefixResolver.getNamespaceURI(prefix));
            }
            List list = nsXPath.selectNodes(nodeService.getPrimaryParent(contextNodeRef));
            HashSet<NodeRef> unique = new HashSet<NodeRef>(list.size());
            for (Object o : list)
            {
                if (!(o instanceof ChildAssociationRef))
                {
                    throw new XPathException("Xpath expression must only select nodes");
                }
                unique.add(((ChildAssociationRef) o).getChildRef());
            }

            List<NodeRef> answer = new ArrayList<NodeRef>(unique.size());
            answer.addAll(unique);
            return answer;
        }
        catch (JaxenException e)
        {
            throw new XPathException("Error executing xpath: \n" + "   xpath: " + xpath, e);
        }
    }

    /**
     * @see NodeServiceXPath
     */
    public List<Serializable> selectProperties(
            NodeRef contextNodeRef,
            String xpath,
            QueryParameterDefinition[] paramDefs,
            NamespacePrefixResolver namespacePrefixResolver,
            boolean followAllParentLinks)
    {
        try
        {
            DocumentNavigator documentNavigator = new DocumentNavigator(
                    dictionaryService,
                    nodeService,
                    searchService,
                    namespacePrefixResolver,
                    followAllParentLinks);
            NodeServiceXPath nsXPath = new NodeServiceXPath(xpath, documentNavigator, paramDefs);
            for (String prefix : namespacePrefixResolver.getPrefixes())
            {
                nsXPath.addNamespace(prefix, namespacePrefixResolver.getNamespaceURI(prefix));
            }
            List list = nsXPath.selectNodes(nodeService.getPrimaryParent(contextNodeRef));
            List<Serializable> answer = new ArrayList<Serializable>(list.size());
            for (Object o : list)
            {
                if (!(o instanceof DocumentNavigator.Property))
                {
                    throw new XPathException("Xpath expression must only select nodes");
                }
                answer.add(((DocumentNavigator.Property) o).value);
            }
            return answer;
        }
        catch (JaxenException e)
        {
            throw new XPathException("Error executing xpath", e);
        }
    }
}
