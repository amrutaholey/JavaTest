# JavaTest
Problem Statement
There is a scenario where thousands of trades are flowing into one store, assume any way of
transmission of trades. We need to create a one trade store, which stores the trade in the following
order



There are couples of validation, we need to provide in the above assignment
1. During transmission if the lower version is being received by the store it will reject the trade and
throw an exception. If the version is same it will override the existing record.
2. Store should not allow the trade which has less maturity date then today date.
3. Store should automatically update expire flag if in a store the trade crosses the maturity date.
FAQ&#39;s
Can I use build management tool, is it mandatory?
Build management tool is not mandatory, but preference is that you should use any one of the build
management tool (Gradle, Maven or Ant). This helps to build the code offline on interviewer’s
machine, without worrying about any dependencies.
How can I share the code with interviewer?
1. In case you are going through offline code pairing session (asynchronous) i.e. you get the
assignment from resourcing team 2 days before, then preferred option is to commit the code in
your GitHub repository. Make the repository public in read only mode and share it with resourcing
team. Interviewer will offline asses the code and at the time of discussion with discuss with you.
2. In case you are coming to office premises for code pairing round (synchronous), then interviewer
will be sitting with you for code pairing session.

Solution:

Run TradeMailn.java class

