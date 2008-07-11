var siteId = null;
if (!json.isNull("site"))
{
   siteId = json.get("site");
}

var params = {};
// Variables we are expecting
var props = [
   "from",
   "to",
   "what",
   "where",
   "desc",
   "datefrom",
   "start",
   "dateto",
   "end",
   "allday",
   "tags"
];

var p;
for(var k=0; k < props.length; k++)
{
   p = props[k];
   if (!json.isNull(p))
   {
      params[p] = json.get(p);
   }
}

model.result = createEvent(siteId, params);

function createEvent(siteId, params)
{
  if (siteId === null)
  {
    return {
      "error": "Site identifier is undefined"
    };
  }

  var site = siteService.getSite(siteId);
  if (site === null)
  {
    return {
      "error": "Could not find specified site"
    };
  }

  var calendar = site.getContainer("calendar");
  if (calendar === null)
  {
    return {
      "error": "Could not get container"
    };
  }
  
  if (!calendar.isTagScope)
  {
     calendar.isTagScope = true;
  }

  var timestamp = new Date().getTime();
  var event = calendar.createNode(timestamp + ".ics", "ia:calendarEvent");

  if (event === null)
  {
    return {
      "error": "Could not create event"
    };
  }

  event.properties["ia:whatEvent"] = params["what"];
  event.properties["ia:whereEvent"] = params["where"];
  event.properties["ia:descriptionEvent"] = params["desc"];

  var fromDate = params["from"];
  var toDate = params["to"];
  
  var allday = params["allday"];
  if (!allday)
  {
     fromDate += " " + params["start"];
     toDate += " " + params["end"];
  }
  
  var tags = params["tags"]; // space delimited string
  if (tags)
  {
     var tagsArray = tags.split(" ");
     event.tags = tagsArray;
  }
  
  var from = new Date(fromDate);
  event.properties["ia:fromDate"] = from;

  var to = new Date(toDate);
  event.properties["ia:toDate"] = to;
  event.save();
  
	try {
		activities.postActivity("org.alfresco.calendar.event-created", siteId, "calendar", '{ "eventName" : ' + params["what"] + ' }');	
	}
	catch(e) {
		if (logger.isLoggingEnabled()) 
		{
			logger.log(e);
		}
	}

  return {
    "name": params["what"],
    "from": from,
    "to": to,
    "uri": "calendar/event/" + siteId + "/" + event.name
  };
};



