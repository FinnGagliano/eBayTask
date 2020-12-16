# Submission to the take home exercise

## 1) Simple variable prices by vehicle
The first thing I did for the first feature was add in the vehicle property to
the Quote class. This was easy enough, all I had to do was add in the vehicle
variable, the getter and the setter.

Then I changed the QuoteController class so that the correct markup would be
applied to the price. I felt a switch case was the most appropriate structure
for deciding what the markup was, dependent on the vehicle input. An assumption
I have made in my code and in testing is that if the user provides a type of
vehicle not in the define list then there should not be any markup applied. I
think for a real website I would have it so that the user *has* to pick one of
the defined vehicles.

Another assumption I made was that the price should be rounded to the nearest
pound. I think that this is appropriate, given that the initial data type for
price was Long.

An adjustment I made to the original tests was adding in the vehicle type and an
appropriately adjusted price.

I have also added some additional tests:
- If two identical postcodes are given, the delivery cost is £0
- If the vehicle provided doesn't match the defined vehicles then no markup is
added


## 2) Build an interface for your app!

This feature was definitely a lot more involved, but overall didn't cause too
much stress. I have introduced a HomeController class which essentially maps a
GET request for the root directory of the website to the index.html file I have
created.

Inside the index.html file is some basic HTML tags to provide user input boxes
as well as a button to submit the request for a quote. At first I was using an
input tag of type submit, which was causing the page to refresh after each
request, so I changed it to an input of type button to fix that.

Once the button is clicked, the page runs some JavaScript. The JS function I
defined takes the user inputs and makes a POST request for a Quote. If the
response is taking time then the page will say 'Loading...', and once the
response has been received it outputs the correct string of information to the
page without having to refresh the page.

I did some testing with the size of my window to make sure the page didn't do
anything strange when the window size changed and all was well from my tests.
