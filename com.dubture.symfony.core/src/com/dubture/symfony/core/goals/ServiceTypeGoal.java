/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Dawid Pakuła <zulus@w3des.net>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.goals;

import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.AbstractGoal;

public class ServiceTypeGoal extends AbstractGoal{


	private final String serviceId;

	public ServiceTypeGoal(IContext context, String serviceId) {
		super(context);
		assert serviceId != null;
		this.serviceId = serviceId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!super.equals(obj)) {
			return false;
		} else if (this.getClass() != obj.getClass()) {
			return false;
		}

		return serviceId.equals(((ServiceTypeGoal) obj).getServiceId());
	}

	@Override
	public int hashCode() {
		return 41 * super.hashCode() + serviceId.hashCode();
	}

	public String getServiceId() {
		return serviceId;
	}

}
