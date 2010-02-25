/*
 * Copyright (C) 2005-2010 Alfresco Software Limited.
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
 * FLOSS exception.  You should have received a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 * http://www.alfresco.com/legal/licensing"
 */
package org.alfresco.cmis;

import java.util.Date;

import org.alfresco.service.cmr.repository.NodeRef;

/**
 * This class describes entry record for some <b>Change Log</b> descriptor.
 * 
 * @author Dmitry Velichkevich
 */
public interface CMISChangeEvent
{    
    /**
     * Gets the change type.
     * 
     * @return {@link CMISChangeType} <b>enum</b> value that determines the type of current <b>Change Event</b>
     */
    public CMISChangeType getChangeType();
    
    /**
     * Gets the change time.
     * 
     * @return {@link Date} value that represents time of current <b>Change Event</b>
     */
    public Date getChangeTime();
    
    /**
     * Gets the changed node (may no longer exist).
     * 
     * @return the changed node
     */
    public NodeRef getChangedNode();

    /**
     * Gets the object id.
     * 
     * @return the object id
     */
    public String getObjectId();
    
}
