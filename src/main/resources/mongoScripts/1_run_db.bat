call conf.bat

call mkdirs.bat

start "" "%mongo_dir%\mongod.exe" ^
--storageEngine=mmapv1 ^
--dbpath "%mongo_data_dir%\db\%db_name1%" ^
--logpath "%mongo_data_dir%\log\%db_name1%\mongo.log" ^
--smallfiles ^
--oplogSize 128 ^
--replSet testReplica ^
--port 27001


start "" "%mongo_dir%\mongod.exe" ^
--storageEngine=mmapv1 ^
--dbpath "%mongo_data_dir%\db\%db_name2%" ^
--logpath "%mongo_data_dir%\log\%db_name2%\mongo.log" ^
--smallfiles ^
--oplogSize 128 ^
--replSet testReplica ^
--port 27002 

start "" "%mongo_dir%\mongod.exe" ^
--storageEngine=mmapv1 ^
--dbpath "%mongo_data_dir%\db\%db_name3%" ^
--logpath "%mongo_data_dir%\log\%db_name3%\mongo.log" ^
--smallfiles ^
--oplogSize 128 ^
--replSet testReplica ^
--port 27003 