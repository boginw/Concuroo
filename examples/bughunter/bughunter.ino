#include <Coroutine.h>
#include <Scheduler/Channel.h>
#include <Scheduler/LinkedList.h>

int __concuroo__a = 23;
void __concuroo__setup();
void __concuroo__loop(void *loop__param__pointer);


void setup()
{
  __concuroo__setup();
  setupManager();
}

void loop()
{
  if (threadAvailable(0))
  {
    start(__concuroo__loop, NULL);
  }
}

void __concuroo__setup()
{
LinkedList<int> goroutines__started;
int __concuroo__b;

while(goroutines__started.size()){
kill(goroutines__started.shift());
}
}void __concuroo__loop(void *loop__param__pointer)
{
LinkedList<int> goroutines__started;
while(goroutines__started.size()){
kill(goroutines__started.shift());
}
}