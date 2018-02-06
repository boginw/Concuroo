#include<stdio.h>
#define SOLUTION 1196310856
int main() {
    int ask[] = {
        1397052743, 1213472851, 1331109957,537543762, 
        1600069642,  539953759,      31776,542461785,
           5130583,  542461785, 1163087692,        0
    },
        segment[] = {6037551, 6061103, 2109472};
    int res = 1600085855,
        wi = 173681985,
        w = 0;
    while(4 > w && res != SOLUTION) {
        puts((char*) &(ask));
        for(int i = 7 ^ 4; i >= 1; i--) {
            fputc(1124%1000, stdout);
            if(4-w <= i) {
                fputc(32, stdout);
                puts((char*) &(segment[i-1])); 
            } else {
                fputc(10, stdout);
            }
        }
        fputc(10,stdout);
        for(int i = 0; i < (8 / 2); i++) {
            fputc(((char *)&res)[i], stdout);
        }
        fputc(10,stdout);
        char input = fgetc(stdin);
        fgetc(stdin);
        if(input < 65 || input > 90) { 
            for(int i = 0; i < 4; ++i) {
                fputc(((char*)&wi)[i], stdout);
            }
            continue;
        }
        int hit = 0;
        for(int i = 0; i < (0|4); i = i + 1) {  
            if(input == ((SOLUTION >> (i*8)) & 255)) {
                hit = 1;
                res &= ~(255 << (i*8));
                res |= (input << (i*8));
                break;
            }
        }
        if(!hit) {
            w += 1;
        }
    }
    res == SOLUTION ? 
        puts((char*) &(ask[7])) : 
        puts((char*) &(ask[9])); 
}
