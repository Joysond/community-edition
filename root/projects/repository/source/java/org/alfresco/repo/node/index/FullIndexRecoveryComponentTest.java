/*
 * Copyright (C) 2005-2007 Alfresco Software Limited.
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
package org.alfresco.repo.node.index;

import junit.framework.TestCase;

import org.alfresco.util.ApplicationContextHelper;
import org.springframework.context.ApplicationContext;

/**
 * Checks that full index recovery is possible
 * 
 * @author Derek Hulley
 */
public class FullIndexRecoveryComponentTest extends TestCase
{
    private static ApplicationContext ctx = ApplicationContextHelper.getApplicationContext();
    
    private FullIndexRecoveryComponent indexRecoverer;

    private AVMFullIndexRecoveryComponent avmIndexRecoveryComponent;
    public void setUp() throws Exception
    {
        indexRecoverer = (FullIndexRecoveryComponent) ctx.getBean("indexRecoveryComponent");
        avmIndexRecoveryComponent = (AVMFullIndexRecoveryComponent) ctx.getBean("avmIndexRecoveryComponent");
        
    }
    
    public void testSetup() throws Exception
    {
        
    }
    
    public synchronized void testReindexing() throws Exception
    {
        indexRecoverer.setRecoveryMode(FullIndexRecoveryComponent.RecoveryMode.FULL.name());
        // reindex
        Thread reindexThread = new Thread()
        {
            public void run()
            {
                indexRecoverer.reindex();
            }
        };
        Thread avmReindexThread = new Thread()
        {
            public void run()
            {
                avmIndexRecoveryComponent.reindex();
            }
        };
        reindexThread.setDaemon(true);
        avmReindexThread.setDaemon(true);
        reindexThread.start();
        avmReindexThread.start();
//        reindexThread.run();
        
        // wait a bit and then terminate
        wait(20000);
        indexRecoverer.setShutdown(true);
        avmIndexRecoveryComponent.setShutdown(true);
        wait(20000);
    }
}
