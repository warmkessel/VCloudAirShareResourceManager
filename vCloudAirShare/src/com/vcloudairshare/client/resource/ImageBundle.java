package com.vcloudairshare.client.resource;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ImageBundle extends ClientBundle{
	
  @Source("pon.png")
  ImageResource poweron();
  
  @Source("poff.png")
  ImageResource poweroff();
  
}
