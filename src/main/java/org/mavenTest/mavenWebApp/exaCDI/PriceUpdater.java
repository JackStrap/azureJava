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

import javax.enterprise.context.*;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The type PriceUpdater
 *
 * @author Peter Pilgrim
 */
@Named
@ApplicationScoped
//@RequestScoped
public class PriceUpdater {

	@Inject
	Event<LivePriceEvent> events;

	public void announce() {
		events.fire(new LivePriceEvent("Digital Jave EE", Math.random() * 30 + 45.0));
	}

}
