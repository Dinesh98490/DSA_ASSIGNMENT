// Imagine a small community with n houses, numbered 0 to n-1. Some houses have restrictions against becoming
// friends, represented by pairs in the restrictions list. For example, if [0, 1] is in the list, houses 0 and 1 cannot be
// directly or indirectly friends (through common friends).
// Residents send friend requests to each other, represented by pairs in the requests list. Your task is to determine if
// each friend request can be accepted based on the current friendship network and the existing restrictions.
// Example2:
// Input:
// Number of houses: 3
// Restrictions: [0, 1]
// [0, 1] (House 0 and House 1 cannot be friends)
// Friend requests: [[0, 2], [2,1]]
// [0, 2] (House 0 requests friendship with House 2)
// [2, 1] (House 2 requests friendship with House 1)
// Outcome: [approved, denied]
// Request 0: Approved (House 0 and 2 don't have any restrictions)
// Request 1: Denied (House 2 and 1 would be indirectly connected through House 0, violating the restriction).
// Example 2:
// Input:
// Number of Houses = 5
// Restrictions = [[0, 1], [1, 2], [2, 3]]
// Requests = [[0, 4], [1, 2], [3, 1], [3, 4]]
// Output: [approved, denied, approved, denied]
// Explanation:
// Request 0: house 0 and house 4 can be friends, so they become direct friends.
// Request 1: house 1 and house 2 cannot be friends since they are directly restricted.
// Request 2: house 3 and house 1 can be friends, so they become direct friends.
// Request 3: house 3 and house 4 cannot be friends since person 0 and person 1 would be indirect friends ((0--4--3--1)



public class q_no_3a {

    
}
