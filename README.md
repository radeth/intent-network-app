We want to calculate cheapest route to deliver data in a network infrastructure where data transfer cost varies between devices.
Create an application that calculates cheapest route between chosen devices in this network. Application should accept following command line arguments:
- path to file with network specification
- ID of source device
- ID of target device
For example:
script.py network.txt 10 89
Input file with network specification will define each connection in new line using format:
<device ID> <transfer cost> <connected device ID>
You can safely assume that transfer cost between 2 devices is same in both directions.
Application should write an output to stdout in format:
<source device ID> -> <intermediate device ID 1> -> <intermediate device ID 2> -> ... -> <intermediate device ID N> -> <target device ID>
--- Example
For input file in.txt with content:
0 30 1
0 70 4
0 10 8
0 10 9
1 90 4
1 40 7
2 50 3
2 40 5
2 90 6
2 100 8
4 100 7
4 70 8
4 50 9
5 100 6
6 70 7
7 10 9
and command line arguments "in.txt 2 7" application should print following output:
2 -> 8 -> 0 -> 9 -> 7-- 


            LongOperationStatusDto cloned = (LongOperationStatusDto)super.clone();
