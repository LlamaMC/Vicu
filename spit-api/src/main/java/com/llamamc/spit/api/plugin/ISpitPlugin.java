package com.llamamc.spit.api.plugin;

import com.llamamc.spit.api.ISpit;

public interface ISpitPlugin {
	void enable(ISpit vicu);
	void disable();
}
