# RedditTop50
Shows the top 50 posts from reddit.

# Notes 
- The jetpack pager was replaced by a classic adapter. This was necessary to implement a correct deletion of items and avoid problems with an immutable list. 
- A navigation graph was defined, but with the last updates is not used anymore, since the app will support landscape in a simple way, keeping two fragments alive and hidding or showing them according the orientation changes.
- An approach about creating a landscape layout was considered, but a more efficient way to solve the same problem was found. 
- Jetpack Pager classes are still visible. They were used in the previous version but not anymore. I'll keep them to work on them later.
- Nav_graph.xml is also still in the project in case I attempt to change the approach to show two fragments in the Main Activity, or I just want to make the transitions work simple and linear.
