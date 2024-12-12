#include <stdio.h>

#define MAX_LINE 128

int main() {
    int rules[2048][2];
    int rules_n = 0;

    int list[256][64];
    int list_n[256];
    int lists_amount = 0;

    int a, b;
    char line[MAX_LINE];
    char hit_spot = 0;
    while (fgets(line, MAX_LINE, stdin)) {
        if (!hit_spot) {
            if (sscanf(line, "%d|%d", &a, &b) == 2) {
                rules[rules_n][0] = a;
                rules[rules_n][1] = b;
                rules_n++;
            } else {
                hit_spot = 1;
            }
        } else {
            int nc;
            char *iter = line;
            char useless;
            int idx = 0;
            while (*iter && sscanf(iter, "%d%c%n", &a, &useless, &nc)) {
                iter += nc;
                list[lists_amount][idx] = a;
                idx++;
            }
            list_n[lists_amount] = idx;
            lists_amount++;
        }
    }

    // for (int i = 0; i < lists_amount; i++) {
    //     for (int j = 0; j < list_n[i]; j++) {
    //         printf("%d ", list[i][j]);
    //     }

    //    printf("\n");
    //}

    int sum = 0;
    int *curr_list;
    int size, num;
    for (int test_case = 0; test_case < lists_amount; test_case++) {
        char fucked = 0;
        curr_list = list[test_case];
        size = list_n[test_case];

        for (int i = 0; i < size; i++) {
            num = curr_list[i];

            int check_num;
            int *curr_rule;
            // check numbers before
            for (int bf = 0; bf < i; bf++) {
                check_num = curr_list[bf];

                for (int rule_idx = 0; rule_idx < rules_n; rule_idx++) {
                    curr_rule = rules[rule_idx];
                    if (curr_rule[0] == num && curr_rule[1] == check_num) {
                        fucked = 1;
                    }
                }
            }

            // check numbers after
            for (int af = i + 1; af < size; af++) {
                check_num = curr_list[af];

                for (int rule_idx = 0; rule_idx < rules_n; rule_idx++) {
                    curr_rule = rules[rule_idx];
                    if (curr_rule[1] == num && curr_rule[0] == check_num) {
                        fucked = 1;
                    }
                }
            }
        }

        if (!fucked) {
            int num_to_sum = curr_list[size / 2];
            printf("OK: %d\n", num_to_sum);
            sum += num_to_sum;
        }
    }
    printf("%d\n", sum);

    return 0;
}
