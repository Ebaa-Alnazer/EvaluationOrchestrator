(defproblem problem movecontainer
((size ${size})
(stage 0)
(truck t0) (at t0 ${truck.getX()} ${truck.getY()})
<#list containers as container>(container c${container?index}) (at c${container?index} ${container.getX()} ${container.getY()})
</#list>
<#list shortcuts as shortcut>(shortcut ${shortcut.getStart().getX()} ${shortcut.getStart().getY()} ${shortcut.getEnd().getX()} ${shortcut.getEnd().getY()})
</#list>
)

((transport-container  c0 t0 ${target.getX()} ${target.getY()})))