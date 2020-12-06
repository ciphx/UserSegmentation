**code is in master branch**

**OBJECTIVE** : 

To segregate users into one or more groups based on their 
preferences. A single user can be a part of multiple groups, 
while a single group can have multiple users.
            
**BRIEF OVERVIEW OF THE LOGIC** :

1. We are using files to save rules for a group. The format of the file 
is similar to "GROUP_A.json".
The file has all individual attributes that are to be considered in
the group, followed by an expression, identifying on the condition
to be applied on the attributes.
2. We load all the rules during application startup and re-use them for
all the users.
3. We evaluate each rule in a group individually and store the result 
in a map
4. We then use janino expression evaluator to parse the boolean expression
to be applied for inclusion in a particular group.
5. We can persist the user id , preferences json , individual group result in a table [User_Rules_Evaluation_Data table]
We use a unique attempt id for every evaluation of a group for a user. This is used as the key for this table.
6. We upsert(update if same, insert if user doesn't exist) the user id, list of groups the user 
has qualified for in the database as well [User_Group_Assignment table] . We use user_id as the key for this table.
7. We can use the User_Rules_Evaluation_Data table to fetch historical results as well, while we keep 
the User_Group_Assignment table updated with the latest results.
8. The database schema is mentioned in schema.sql


**To start the application** :
Please use lombok plugin to not get errors for getters/setters
Use JDK 8 as the project SDK (open api jdk has some conflicts with spring jpa, so it would be advisable to not use that)
Run Application.class
The rest url is : localhost:8080/v1/matchUserToGroups
Sample payload :
{
  "attempt_id": "3xcd",
  "user_id" : 1,
  "gender" : "female",
  "preference.food" : "red-meat",
  "order_count" : "100",
  "location" : "bengaluru"
}
**To view the tables and data, use the H2 UI , use "http://localhost:8080/h2-console/" [no password is required ]**

**We have used spring-boot, testng, janino, lombok in the codebase.**
