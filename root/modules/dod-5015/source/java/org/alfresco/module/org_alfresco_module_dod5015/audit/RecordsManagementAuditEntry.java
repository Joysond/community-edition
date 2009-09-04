/*
 * Copyright (C) 2005-2008 Alfresco Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in Alfresco's 
 * FLOSS exception.  You should have recieved a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 * http://www.alfresco.com/legal/licensing"
 */
package org.alfresco.module.org_alfresco_module_dod5015.audit;

import java.util.Date;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.util.ISO8601DateFormat;
import org.alfresco.util.ParameterCheck;

/**
 * Class to represent a Records Management audit entry.
 * 
 * @author Gavin Cornwell
 */
public final class RecordsManagementAuditEntry
{
    private final Date timestamp;
    private final String userName;
    private final String fullName;
    private final String userRole;
    private final NodeRef nodeRef;
    private final String nodeName;
    private final String event;
 
    /**
     * Default constructor
     */
    public RecordsManagementAuditEntry(Date timestamp, 
                String userName, String fullName, String userRole, 
                NodeRef nodeRef, String nodeName, String event)
    {
        ParameterCheck.mandatory("timestamp", timestamp);
        ParameterCheck.mandatory("userName", userName);
        
        this.timestamp = timestamp;
        this.userName = userName;
        this.userRole = userRole;
        this.fullName = fullName;
        this.nodeRef = nodeRef;
        this.nodeName = nodeName;
        this.event = event;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("(")
          .append("timestamp=").append(timestamp)
          .append(", userName=").append(userName)
          .append(", userRole=").append(userRole)
          .append(", fullName=").append(fullName)
          .append(", nodeRef=").append(nodeRef)
          .append(", nodeName=").append(nodeName)
          .append(", event=").append(event)
          .append(")");
        return sb.toString();
    }

    /**
     * 
     * @return The date of the audit entry
     */
    public Date getTimestamp()
    {
        return this.timestamp;
    }
    
    /**
     * 
     * @return The date of the audit entry as an ISO8601 formatted String
     */
    public String getTimestampString()
    {
        return ISO8601DateFormat.format(this.timestamp);
    }

    /**
     * 
     * @return The username of the user that caused the audit log entry to be created
     */
    public String getUserName()
    {
        return this.userName;
    }

    /**
     * 
     * @return The full name of the user that caused the audit log entry to be created
     */
    public String getFullName()
    {
        return this.fullName;
    }

    /**
     * 
     * @return The role of the user that caused the audit log entry to be created
     */
    public String getUserRole()
    {
        return this.userRole;
    }

    /**
     * 
     * @return The NodeRef of the node the audit log entry is for
     */
    public NodeRef getNodeRef()
    {
        return this.nodeRef;
    }

    /**
     * 
     * @return The name of the node the audit log entry is for
     */
    public String getNodeName()
    {
        return this.nodeName;
    }

    /**
     * 
     * @return The human readable description of the reason for the audit log 
     *         entry i.e. metadata updated, record declared
     */
    public String getEvent()
    {
        return this.event;
    }
}
