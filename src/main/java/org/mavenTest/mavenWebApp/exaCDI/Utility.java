/*******************************************************************************
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2014,2015,2016 by Peter Pilgrim, Milton Keynes, P.E.A.T LTD
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPL v3.0
 * which accompanies this distribution, and is available at:
 * http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Developers:
 * Peter Pilgrim -- design, development and implementation
 *               -- Blog: http://www.xenonique.co.uk/blog/
 *               -- Twitter: @peter_pilgrim
 *
 * Contributors:
 *
 *******************************************************************************/

package org.mavenTest.mavenWebApp.exaCDI;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;

/**
 * Created by peter.pilgrim on 20-Oct-2015.
 */
@ApplicationScoped
public class Utility implements Serializable {

    private static final long serialVersionUID = 1L;

	public String debugHashCode( Object ref  ) {
        if ( ref == null) return "null";
        return String.format( "%s#%X", ref.getClass().getName(), System.identityHashCode(ref));
    }
}
