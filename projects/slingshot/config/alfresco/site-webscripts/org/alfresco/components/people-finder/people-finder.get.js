function main()
{
   // Widget instantiation metadata...
   var searchConfig = config.scoped['Search']['search'],
       defaultMinSearchTermLength = searchConfig.getChildValue('min-search-term-length'),
       defaultMaxSearchResults = searchConfig.getChildValue('max-search-results');

   var peopleFinder = {
      id : "PeopleFinder", 
      name : "Alfresco.PeopleFinder",
      options : {
         userId : user.name,
         siteId : (this.page != null) ? ((this.page.url.templateArgs.site != null) ? this.page.url.templateArgs.site : "") : ((args.site != null) ? args.site : ""),
         minSearchTermLength : parseInt((args.minSearchTermLength != null) ? args.minSearchTermLength : defaultMinSearchTermLength),
         maxSearchResults : parseInt((args.maxSearchResults != null) ? args.maxSearchResults : defaultMaxSearchResults),
         setFocus : Boolean((args.setFocus != null) ? args.setFocus : "false"),
         addButtonSuffix : (args.addButtonSuffix != null) ? args.addButtonSuffix : "",
         dataWebScript : ((args.dataWebScript != null) ? args.dataWebScript : "api/people").replace("[", "{").replace("]", "}"),
         viewMode : { _alfValue : "Alfresco.PeopleFinder.VIEW_MODE_FULLPAGE", _alfType: "REFERENCE"}
      }
   };
   model.widgets = [peopleFinder];
}

main();

