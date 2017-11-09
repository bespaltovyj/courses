var config={   _id: "testReplica",
			members: [
         { _id: 0, priority : 3, host : "localhost:27001" },
         { _id: 1, priority:0, slaveDelay : 15,host : "localhost:27002" },
         { _id: 2, host : "localhost:27003", arbiterOnly : true}
      ]
};
var err=rs.initiate(config);
printjson(err);
rs.status();