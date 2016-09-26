package org.bigbluebutton.core.meeting.filters

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.domain._

class DefaultAbilitiesFilterTests extends UnitSpec {
  it should "eject user" in {
    object DefPerm extends DefaultAbilitiesFilter
    val perm: Set[Ability] = Set(CanEjectUser, CanRaiseHand)
    assert(DefPerm.can(CanEjectUser, perm))
  }

  it should "not eject user" in {
    object DefPerm extends DefaultAbilitiesFilter
    val perm: Set[Ability] = Set(CanRaiseHand)
    assert(DefPerm.can(CanEjectUser, perm) != true)
  }

  it should "calculate abilities based on roles" in {
    val roles: Set[Role] = Set(ModeratorRole, PresenterRole)
    object DefPerm extends DefaultAbilitiesFilter
    val perm: Set[Ability] = DefPerm.calcRolesAbilities(roles)
    assert(DefPerm.can(CanSharePresentation, perm) == true)
  }
}
