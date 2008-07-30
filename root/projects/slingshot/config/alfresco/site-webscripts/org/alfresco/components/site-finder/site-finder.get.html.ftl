<script type="text/javascript">//<![CDATA[
   new Alfresco.SiteFinder("${args.htmlid}").setOptions(
   {
      showPrivateSites: true,
      currentUser: "${user.id}"
   }).setMessages(
      ${messages}
   );
//]]></script>

<div id="${args.htmlid}-body" class="site-finder">
	
	<div class="heading">${msg("site-finder.heading")}</div>
	
	<div class="search-controls">
	   <input id="${args.htmlid}-term" type="text" class="search-term" />
	   <input id="${args.htmlid}-button" type="button" value="${msg("site-finder.search-button")}" />
	</div>
	
	<#-- this div contains the site search results -->
	<div id="${args.htmlid}-sites" class="site-list"></div>
	
</div>