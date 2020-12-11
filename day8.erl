#!/usr/bin/env escript

-record(program, {acc=0, i=0, instructions=dict:new()}).
-record(instruction, {op, arg}).

main(_) ->
    {ok, Data} = file:read_file("day8.txt"),
    P = #program{instructions=parse(Data)},
    % Part 1.
    {AccStuck, _, _} = run(P),
    io:format("Accumulator before stuck: ~B\n", [AccStuck]),
    % Part 2
    {AccDone, _, _} = fix(P),
    io:format("Accumulator after fix: ~B\n", [AccDone]).

% Process input data
parse(Data) ->
    parse(dict:new(), 0, binary:split(Data, [<<"\n">>], [global, trim])).
parse(Instructions, I, [Instruction|Rest]) ->
    parse(dict:store(I, wrap(Instruction), Instructions), I + 1, Rest);
parse(D, _I, []) ->
    D.

wrap(Instruction) ->
    [Op, Arg] = string:split(Instruction, " "),
    {ArgInt, _} = string:to_integer(Arg),
    #instruction{op=Op, arg=ArgInt}.

% Run program
is_stuck(P, V) ->
    sets:is_element(P#program.i, V).
is_done(P, I) ->
    I >= dict:size(P#program.instructions).

run(P) ->
    run(P, sets:new()).
run(P, V) ->
    run(P, V, is_stuck(P, V), is_done(P, P#program.i)).
run(P, V, false, false) ->
    NewV = sets:add_element(P#program.i, V),
    NewP = exec(P, dict:fetch(P#program.i, P#program.instructions)),
    run(NewP, NewV, is_stuck(NewP, NewV), is_done(NewP, NewP#program.i));
run(P, _, Stuck, Done) ->
    {P#program.acc, Stuck, Done}.

exec(P, #instruction{op = <<"acc">>, arg = Arg}) ->
    P#program{acc=P#program.acc + Arg, i=P#program.i + 1};
exec(P, #instruction{op = <<"jmp">>, arg = Arg}) ->
    P#program{i=P#program.i + Arg};
exec(P, #instruction{op = <<"nop">>}) ->
    P#program{i=P#program.i + 1}.

% Fix program
fix(#program{}=P) ->
    fix(P, 0).
fix(#program{}=P, I) ->
    fix(P, I, is_done(P, I)).
fix(P, I, false) ->
    fix(P, I, swap(dict:fetch(I, P#program.instructions)));
fix(P, I, {ok, Instruction}) ->
    NewInstructions = dict:store(I, Instruction, P#program.instructions),
    NewP = P#program{instructions=NewInstructions},
    fix(P, I, run(NewP));
fix(_P, _I, {Acc, false, true}) ->
    {Acc, false, true};
fix(P, I, _) ->
    fix(P, I + 1).

swap(#instruction{op = <<"jmp">>} = Instruction) ->
    {ok, Instruction#instruction{op = <<"nop">>}};
swap(#instruction{op = <<"nop">>} = Instruction) ->
    {ok, Instruction#instruction{op = <<"jmp">>}};
swap(Instruction) ->
    {error, Instruction}.

