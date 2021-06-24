package com.example.home.ApacheCamelRestExample.pojo;

import java.io.IOException;

/*
*
* Copyright 2013 Netflix, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*/

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;

/**
 * Ping implementation if you want to do a "health check" kind of Ping. This will be a "real" ping. As in a real http/s
 * call is made to this url e.g. http://ec2-75-101-231-85.compute-1.amazonaws.com:7101/cs/hostRunning
 *
 * Some services/clients choose PingDiscovery - which is quick but is not a real ping. i.e It just asks discovery
 * (eureka) in-memory cache if the server is present in its Roster PingUrl on the other hand, makes an actual call. This
 * is more expensive - but its the "standard" way most VIPs and other services perform HealthChecks.
 *
 * Choose your Ping based on your needs.
 *
 * @author stonse
 *
 */
public class PingUrl implements IPing {
    private static final Logger LOGGER = LoggerFactory.getLogger(PingUrl.class);

    public static void main(final String[] args) {
//	PingUrl p = new PingUrl(false, "/cs/hostRunning");
//	p.setExpectedContent("true");
//	Server s = new Server("ec2-75-101-231-85.compute-1.amazonaws.com", 7101);
//
//	boolean isAlive = p.isAlive(s);
//	System.out.println("isAlive:" + isAlive);

	PingUrl p = new PingUrl(false, "/actuator/health");
	p.setExpectedContent("UP");
	Server s = new Server("localhost", 9090);

	boolean isAlive = p.isAlive(s);
	System.out.println("isAlive:" + isAlive);
    }

    String pingAppendString = "";

    boolean isSecure = false;

    /*
     *
     * Send one ping only.
     *
     * Well, send what you need to determine whether or not the server is still alive. Should return within a
     * "reasonable" time.
     */

    String expectedContent = null;

    public PingUrl() {
    }

    public PingUrl(final boolean isSecure, final String pingAppendString) {
	this.isSecure = isSecure;
	this.pingAppendString = pingAppendString != null ? pingAppendString : "";
    }

    public PingUrl(final boolean isSecure, final String pingAppendString, final String expectedContent) {
	this.isSecure = isSecure;
	this.pingAppendString = pingAppendString != null ? pingAppendString : "";
	this.expectedContent = expectedContent;
    }

    public String getExpectedContent() {
	return expectedContent;
    }

    public String getPingAppendString() {
	return pingAppendString;
    }

    @Override
    public boolean isAlive(final Server server) {
	String urlStr = "";
	if (isSecure) {
	    urlStr = "https://";
	} else {
	    urlStr = "http://";
	}
	urlStr += server.getId();
	urlStr += getPingAppendString();

	boolean isAlive = false;

	HttpClient httpClient = new DefaultHttpClient();
	HttpUriRequest getRequest = new HttpGet(urlStr);
	String content = null;
	try {
	    HttpResponse response = httpClient.execute(getRequest);
	    content = EntityUtils.toString(response.getEntity());
	    isAlive = response.getStatusLine().getStatusCode() == 200;
	    if (getExpectedContent() != null) {
		if (content == null) {
		    isAlive = false;
		} else {
		    String status = new ObjectMapper().readTree(content).path("status").asText();
		    if (status.equals(getExpectedContent())) {
			isAlive = true;
		    } else {
			isAlive = false;
		    }
		}
	    }
	} catch (IOException e) {
	    LOGGER.error(e.getMessage(), e);
	} finally {
	    // Release the connection.
	    getRequest.abort();
	}

	return isAlive;
    }

    public boolean isSecure() {
	return isSecure;
    }

    /**
     * Is there a particular content you are hoping to see? If so -set this here. for e.g. the WCS server sets the
     * content body to be 'true' Please be advised that this content should match the actual content exactly for this to
     * work. Else yo may get false status.
     *
     * @param expectedContent
     */
    public void setExpectedContent(final String expectedContent) {
	this.expectedContent = expectedContent;
    }

    public void setPingAppendString(final String pingAppendString) {
	this.pingAppendString = pingAppendString != null ? pingAppendString : "";
    }

    /**
     * Should the Secure protocol be used to Ping
     *
     * @param isSecure
     */
    public void setSecure(final boolean isSecure) {
	this.isSecure = isSecure;
    }
}
