#include <Coroutine.h>
#include <Scheduler/Channel.h>
#include <Scheduler/LinkedList.h>

struct flash__params__struct
{
int __concuroo__led;
Channel<int > *__concuroo__channel;
};
struct sender__params__struct
{
Channel<int > *__concuroo__channel;
};
int __concuroo__DEL = 250;
int __concuroo__LED1 = 12;
int __concuroo__LED2 = 11;
void __concuroo__setup();
void __concuroo__loop(void *loop__param__pointer);
void __concuroo__flashForAWhile(int __concuroo__led, Channel<int > *__concuroo__channel);
void __concuroo__flash(void *flash__param__pointer);
void __concuroo__sender(void *sender__param__pointer);


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
pinMode(__concuroo__LED1,1);
pinMode(__concuroo__LED2,1);
while(goroutines__started.size()){
kill(goroutines__started.shift());
}
}void __concuroo__loop(void *loop__param__pointer)
{
LinkedList<int> goroutines__started;
Channel<int > *__concuroo__channel = new Channel<int >();

sender__params__struct sender__params__0 = {__concuroo__channel};
goroutines__started.add(start(__concuroo__sender, (void*)(&sender__params__0)));
flash__params__struct flash__params__1 = {__concuroo__LED1, __concuroo__channel};
goroutines__started.add(start(__concuroo__flash, (void*)(&flash__params__1)));
delay(__concuroo__DEL);
{
while(goroutines__started.size()){
kill(goroutines__started.shift());
}return;
}
while(1){
__concuroo__flashForAWhile(__concuroo__LED2,__concuroo__channel);
}
sizeof(int);
{
while(goroutines__started.size()){
kill(goroutines__started.shift());
}return 14;
}
{
while(goroutines__started.size()){
kill(goroutines__started.shift());
}return 14.14;
}
{
while(goroutines__started.size()){
kill(goroutines__started.shift());
}return "string";
}
{
while(goroutines__started.size()){
kill(goroutines__started.shift());
}return 'c';
}
delete __concuroo__channel;
while(goroutines__started.size()){
kill(goroutines__started.shift());
}
}void __concuroo__flashForAWhile(int __concuroo__led, Channel<int > *__concuroo__channel)
{
LinkedList<int> goroutines__started;
delay((__concuroo__DEL*5));
flash__params__struct flash__params__2 = {__concuroo__LED2, __concuroo__channel};
goroutines__started.add(start(__concuroo__flash, (void*)(&flash__params__2)));
delay((__concuroo__DEL*5));
while(goroutines__started.size()){
kill(goroutines__started.shift());
}
}void __concuroo__flash(void *flash__param__pointer)
{
LinkedList<int> goroutines__started;
flash__params__struct *flash__params = (flash__params__struct *)flash__param__pointer;
int __concuroo__led = flash__params->__concuroo__led;
Channel<int > *__concuroo__channel = flash__params->__concuroo__channel;

while(true){
digitalWrite(__concuroo__led,1);
delay(__concuroo__channel->recval());
digitalWrite(__concuroo__led,0);
delay(__concuroo__channel->recval());
}
while(goroutines__started.size()){
kill(goroutines__started.shift());
}
}void __concuroo__sender(void *sender__param__pointer)
{
LinkedList<int> goroutines__started;
sender__params__struct *sender__params = (sender__params__struct *)sender__param__pointer;
Channel<int > *__concuroo__channel = sender__params->__concuroo__channel;

int __concuroo__del = __concuroo__DEL;

while(1){
__concuroo__channel->sendval(__concuroo__del);
}
while(goroutines__started.size()){
kill(goroutines__started.shift());
}
}