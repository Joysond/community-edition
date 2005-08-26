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
package org.alfresco.repo.security.permissions.impl;

import java.util.Set;

import org.alfresco.repo.security.permissions.PermissionReference;
import org.alfresco.repo.security.permissions.impl.model.RequiredPermission;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

/**
 * The API for the alfresco permission model.
 * 
 * @author andyh
 */
public interface ModelDAO
{

    /**
     * Get the permissions that can be set for the given type.
     * 
     * @param type - the type in the data dictionary.
     * @return
     */
    public Set<PermissionReference> getAllPermissions(QName type);

    /**
     * Get the permissions that can be set for the given node. 
     * This is determined by the node type.
     * 
     * @param nodeRef
     * @return
     */
    public Set<PermissionReference> getAllPermissions(NodeRef nodeRef);
    
    /**
     *Get the permissions that are exposed to be set for the given type.
     * 
     * @param type - the type in the data dictionary.
     * @return
     */
    public Set<PermissionReference> getExposedPermissions(QName type);

    /**
     * Get the permissions that are exposed to be set for the given node. 
     * This is determined by the node type.
     * 
     * @param nodeRef
     * @return
     */
    public Set<PermissionReference> getExposedPermissions(NodeRef nodeRef);

    /**
     * Get all the permissions that grant this permission.
     * 
     * @param perm
     * @return
     */
    public Set<PermissionReference> getGrantingPermissions(PermissionReference perm);

    /**
     * Get the permissions that must also be present on the node for the required permission to apply.
     *  
     * @param required
     * @param qName
     * @param aspectQNames
     * @param on
     * @return
     */
    public Set<PermissionReference> getRequiredPermissions(PermissionReference required, QName qName, Set<QName> aspectQNames, RequiredPermission.On on);

    /**
     * Get the permissions which are granted by the supplied permission.
     * 
     * @param permissionReference
     * @return
     */
    public Set<PermissionReference> getGranteePermissions(PermissionReference permissionReference);

    /**
     * Is this permission refernce to a permission and not a permissoinSet?
     * 
     * @param required
     * @return
     */
    public boolean checkPermission(PermissionReference required);

}
