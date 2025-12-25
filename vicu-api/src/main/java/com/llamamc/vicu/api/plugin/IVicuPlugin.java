package com.llamamc.vicu.api.plugin;

import com.llamamc.vicu.api.IVicu;

public interface IVicuPlugin {
	void enable(IVicu vicu);
	void disable();
}
