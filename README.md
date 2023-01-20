# BudgetManager
Manage your daily income and expenses with BudgetManager. Categorize, track, and stay on top of your finances with ease.

# Rest APIs Documentation

### Authorization

| METHOD |        URL        | DESCRIPTION | SAMPLE REQUEST BODY |
|:------:|:-----------------:|:-----------:|:-------------------:|
|  POST  |   auth/api/login  |    Log in   |         JSON        |
|  POST  | auth/api/register |   Register  |         JSON        |


### Users

| METHOD |         URL         |           DESCRIPTION           | SAMPLE REQUEST BODY |
|:------:|:-------------------:|:-------------------------------:|:-------------------:|
|   GET  | api/user/find/{id} |     Find user info by userId    |         JSON        |
|   GET  | api/users/find/all | Find info about all users in db |         N/A         |

### Budget

| METHOD |                   URL                  |                                       DESCRIPTION                                      | SAMPLE REQUEST BODY |
|:------:|:--------------------------------------:|:--------------------------------------------------------------------------------------:|:-------------------:|
|  POST  |             api/budget/add             |                      Add new budget(Needs to be logged/authorized)                     |         JSON        |
|   GET  |             api/budget/list            |                     Show all budget(Needs to bo logged/authorized)                     |         N/A         |
|   GET  |        api/budget/count/incomes        |                        Returns an Integer value of total income                        |         N/A         |
|   GET  |        api/budget/count/incomes        |                        Returns an Integer value of total expense                       |         N/A         |
|   GET  |         api/budget/count/total         | Returns an Integer value which is a difference between total income and total expense. |         N/A         |
|   GET  |       api/budget/find/userid/{id}      |                                Find all budget by userId                               |         N/A         |
|   GET  | api/budget/find/history/{userid}/{day} |                        Find all budget by user id and day number                       |         N/A         |

### History
| METHOD |            URL            |                                        DESCRIPTION                                       | SAMPLE REQUEST BODY |
|:------:|:-------------------------:|:----------------------------------------------------------------------------------------:|:-------------------:|
|  POST  |     api/history/create    |                  Create next history day (Needs to be logged/authorized)                 |         N/A         |
|   GET  | api/history/highestnumber | Returns an Integer of highest day number for logged user (Needs to be logged/authorized) |         N/A         |
|   GET  |   api/history/find/{id}   |                              Find all history days by userId                             |         N/A         |
