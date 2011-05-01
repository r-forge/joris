package org.rosuda.irconnect.cfg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rosuda.irconnect.IRConnection;
import org.rosuda.irconnect.IREXP;
import org.rosuda.irconnect.RServerException;
import org.springframework.beans.factory.annotation.Required;

public class LibraryInstallationStep implements IRConnectionConfigStep{

	Log LOG = LogFactory.getLog(LibraryInstallationStep.class);
	
	private String library;
	
	@Required
	public void setLibrary(final String library) {
		this.library = library;
	}
	
	@Override
	public boolean doWithConnection(final IRConnection con) throws RServerException {
		final String libraryExistsCmd = new StringBuilder("(which(.packages(all.available=TRUE)==\"").append(library).append("\")>0)").toString();
		final IREXP libraryIsAlreadyInstalled = con.eval(libraryExistsCmd);
		if (libraryIsAlreadyInstalled.asBool().isTRUE()) {
			LOG.info("library "+library+" has been installed successfully");
			return true;
		}
		final String installLibraryCmd = new StringBuilder("install.packages(\""+library+"\",repos=\"http://cran.r-project.org\")").toString();
		//proxy errors might occur behind firewall, this kind of installation is unsave but might work on lots of machines!
		con.voidEval(installLibraryCmd);
		final IREXP libraryHasSuccessfullyBeenInstalled = con.eval(libraryExistsCmd);
		LOG.info("library "+library+" has "+((libraryHasSuccessfullyBeenInstalled.asBool().isFALSE())?"NOT ":"")+" been installed successfully");
		//if you rely on the libraray you might throw an error or do some logging - but do not assume auto installation will work everywhere
		return (libraryHasSuccessfullyBeenInstalled.asBool().isTRUE());
	}

}
