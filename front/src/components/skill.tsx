interface IProps{
    index: number,
    title: string,
    onRemove: any,
}
function Skill({index,title,onRemove}:IProps) {
    return <li>
    <span>{title}</span>
    <button type="button" onClick={() => onRemove(index)}>
      Remove
    </button>
  </li>
}
export default Skill