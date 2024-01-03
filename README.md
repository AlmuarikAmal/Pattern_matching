# Pattern_matching
## Bioinformatics

Bioinformatics is the management and analysis of biological dataset information. DNA pattern matching is an essential component of bioinformatics, and the challenge of locating subsequences inside a large DNA sequence has several applications in computational biology. A pattern (keyword) is an ordered sequence of symbols. Where symbols of the pattern and the searched text are chosen from a predetermined finite set, called an alphabet (Σ). In general alphabet can be any finite set of symbols/letters.
In bioinformatics:
• DNA alphabet Σ = {A,C,G,T},
• RNA alphabet Σ = {A,C,G,U};
• protein alphabet Σ = {A, R, N, ...V }(20 amino acids).
The patterns in the strands of DNA, RNA, and proteins have important biological meanings, e.g., they are promoters, enhancers, operators, genes, introns, exons, etc.

- Part 1: Brute Force
The problem is to find one, several, or all occurrences of a pattern P with m characters in a DNA sequence S with n characters in total. We note that m < n. In some cases, especially in bioinformatics applications m << n.
Solve the problem using Brute Force BF approach by sliding a window of length m (pattern) over the sequence (of length n) from left to right one letter at a time.
How to enhance it? Try smart shift.

- Part 2: Smart shift

By preprocessing the pattern and building an auxiliary array, shift-amount, if we discover a mismatch (after some matches), we are already aware of some of the characters in the text of the following window. We use this knowledge to prevent matching the characters that we know already.
Consider pattern (P)=abcxabcde, and we are looking in long string S: S= ................abcxabcdv.................
P= abcxabcde
The mismatch occurs when comparing e with v. BF will shift by 1 so S= ................abcxabcdv.................
P= abcxabcde
1
While using smart shift, we can determine that if a mismatch happened when comparing pat- tern[8] with S[i], we can shift pattern by four chars -according to shift-amount-, and compare pattern[4] with S[i], without missing any possible matches.
S= ................abcxabcdv.................
P= abcxabcde
Thus, we save a total of six comparisons! Examples of how to construct a shift-amount:
• For the pattern “CCC”, shift-amount is [0, 1, 2]
• For the pattern “ACG”, shift-amount is [0, 0, 0]
• For the pattern “AAGAACAAA”, shift-amount is [0, 1, 0, 1, 2, 0, 1, 2,3]

- Part 3: Aho-Corasick algorithm

The expected output is:
• The pattern exist in case the pattern shows up in your data; otherwise, it The pattern
does not exist.
